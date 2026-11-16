
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private record Task(int id, String title, boolean done) {}

    private static final Map<Integer, Task> tasks =
            new ConcurrentHashMap<>();

    private static final AtomicInteger nextId =
            new AtomicInteger(1);

    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(
            System.getenv().getOrDefault("PORT", "8080")
        );

        HttpServer server = HttpServer.create(
            new InetSocketAddress(port), 0
        );

        server.createContext("/", Main::handleRequest);
        server.start();

        System.out.println(
            "TaskTrack running at http://localhost:" + port
        );
    }

    private static void handleRequest(HttpExchange ex)
            throws IOException {

        String path = ex.getRequestURI().getPath();
        String method = ex.getRequestMethod();

        if (path.equals("/health") && method.equals("GET")) {
            respond(ex, 200, "OK", "text/plain");
            return;
        }

        if (path.equals("/") && method.equals("GET")) {
            respond(ex, 200, renderPage(), "text/html");
            return;
        }

        if (path.equals("/add") && method.equals("POST")) {
            String title = getFormValue(ex, "title").trim();

            if (!title.isBlank()) {
                int id = nextId.getAndIncrement();
                tasks.put(id, new Task(id, title, false));
            }

            redirect(ex);
            return;
        }

        if (method.equals("POST") &&
            path.matches("/toggle/[0-9]+")) {

            int id = Integer.parseInt(path.substring(8));

            tasks.computeIfPresent(id, (key, task) ->
                new Task(id, task.title(), !task.done())
            );

            redirect(ex);
            return;
        }

        if (method.equals("POST") &&
            path.matches("/delete/[0-9]+")) {

            int id = Integer.parseInt(path.substring(8));
            tasks.remove(id);

            redirect(ex);
            return;
        }

        respond(ex, 404, "Page not found", "text/plain");
    }

    private static String getFormValue(
            HttpExchange ex, String name) throws IOException {

        String body = new String(
            ex.getRequestBody().readAllBytes(),
            StandardCharsets.UTF_8
        );

        for (String field : body.split("&")) {
            String[] parts = field.split("=", 2);

            if (parts.length == 2 && parts[0].equals(name)) {
                return URLDecoder.decode(
                    parts[1], StandardCharsets.UTF_8
                );
            }
        }

        return "";
    }

    private static String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }

    private static String renderPage() {

        StringBuilder items = new StringBuilder();

        tasks.values().stream()
            .sorted(Comparator.comparingInt(Task::id))
            .forEach(task -> {

                items.append("<div class='task'>");

                if (task.done()) {
                    items.append("<span class='done'>");
                } else {
                    items.append("<span>");
                }

                items.append(escapeHtml(task.title()));
                items.append("</span>");

                items.append("<form method='post' action='/toggle/")
                     .append(task.id()).append("'>")
                     .append("<button>Toggle</button></form>");

                items.append("<form method='post' action='/delete/")
                     .append(task.id()).append("'>")
                     .append("<button class='delete'>Delete</button>")
                     .append("</form></div>");
            });

        if (items.length() == 0) {
            items.append("<p>No tasks yet. Add your first task!</p>");
        }

        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport"
                    content="width=device-width, initial-scale=1">
              <title>TaskTrack Java</title>
              <style>
                body {
                  font-family: Arial, sans-serif;
                  background: #eef3fa;
                  padding: 35px 15px;
                }
                .container {
                  max-width: 650px;
                  margin: auto;
                  background: white;
                  padding: 30px;
                  border-radius: 12px;
                  box-shadow: 0 4px 18px #0001;
                }
                h1 { color: #174ea6; }
                .add-form { display: flex; gap: 8px; }
                input {
                  padding: 12px;
                  flex: 1;
                  min-width: 0;
                  border: 1px solid #ccc;
                  border-radius: 6px;
                }
                button {
                  background: #174ea6;
                  color: white;
                  border: none;
                  border-radius: 6px;
                  padding: 11px;
                  cursor: pointer;
                }
                .task {
                  display: flex;
                  align-items: center;
                  gap: 10px;
                  padding: 14px 0;
                  border-bottom: 1px solid #eee;
                }
                .task span {
                  flex: 1;
                  overflow-wrap: anywhere;
                }
                .done {
                  text-decoration: line-through;
                  color: gray;
                }
                .delete { background: #d43b3b; }
              </style>
            </head>
            <body>
              <main class="container">
                <h1>TaskTrack Java</h1>
                <p>Simple Task Management Application</p>
                <form class="add-form" method="post" action="/add">
                  <input name="title" placeholder="Enter a new task"
                         required maxlength="200">
                  <button type="submit">Add Task</button>
                </form>
                <h2>My Tasks</h2>
                __ITEMS__
              </main>
            </body>
            </html>
            """.replace("__ITEMS__", items.toString());
    }

    private static void redirect(HttpExchange ex)
            throws IOException {

        ex.getResponseHeaders().set("Location", "/");
        ex.sendResponseHeaders(303, -1);
        ex.close();
    }

    private static void respond(
            HttpExchange ex, int status,
            String body, String contentType) throws IOException {

        byte[] data = body.getBytes(StandardCharsets.UTF_8);

        ex.getResponseHeaders().set(
            "Content-Type", contentType + "; charset=UTF-8"
        );

        ex.sendResponseHeaders(status, data.length);

        try (var output = ex.getResponseBody()) {
            output.write(data);
        }
    }
}
