# TaskTrack-Java

**Simple Task Management Application Using Java & Docker**

## 1. About the Project | عن المشروع

TaskTrack-Java is a simple web application built using Java. It helps users add, complete, and delete their daily tasks.

المشروع عبارة عن تطبيق ويب بسيط عملته باستخدام Java. فكرته إنه يساعد المستخدم يرتب مهامه اليومية، ويضيف مهام جديدة أو يحدد اللي خلصها أو يحذفها.

## 2. Features | مميزات المشروع

- **Add Task:** Add a new task.
- **View Tasks:** See all tasks.
- **Complete Task:** Mark a task as completed.
- **Delete Task:** Remove a task.

يعني المستخدم بقدر يضيف مهمة، ويشوف المهام الموجودة، ويحدد المهمة اللي خلصها، أو يحذف أي مهمة مش محتاجها.

## 3. Technologies | التقنيات المستخدمة

- Java 21
- HTML & CSS
- Docker
- Git & GitHub

استخدمت Java لعمل التطبيق، وHTML وCSS للواجهة، وDocker عشان أشغّل المشروع داخل Container، وGitHub عشان أحفظ الكود والتعديلات.

## 4. Run Locally | تشغيل المشروع على الجهاز

**Requirements:** Java JDK 21 or newer.

First, compile the Java code:

`javac -encoding UTF-8 -d out src/Main.java`

Then run the application:

`java -cp out Main`

Open the browser:

http://localhost:8080

إذا بدنا نشغّل المشروع بدون Docker، أول إشي لازم تكون Java مثبتة. بعدين بنعمل Compile للكود، وبنشغّل التطبيق، وبنفتح الرابط في المتصفح.

## 5. Run with Docker | تشغيل المشروع باستخدام Docker

**Requirements:** Docker Desktop installed and running.

**Step 1: Build the Docker Image**

`docker build -t tasktrack-java .`

أول خطوة بنبني Docker Image فيها التطبيق والبيئة اللي بحتاجها للتشغيل.

**Step 2: Run the Container**

`docker run -d --name tasktrack-container -p 8080:8080 tasktrack-java`

بعد ما ينجح الـBuild، بنشغّل التطبيق داخل Container على Port 8080.

**Step 3: Check the Container**

`docker ps`

بنستخدم هاد الأمر عشان نتأكد إنه الـContainer شغّال.

**Step 4: Open the Application**

http://localhost:8080

هيك بنقدر نفتح التطبيق من المتصفح ونجربه.

### Run with Docker Compose | التشغيل باستخدام Docker Compose

`docker compose up -d --build`

To stop it:

`docker compose down`

**Note:** Stop `tasktrack-container` first, because both use port 8080.

بدل ما نكتب أوامر الـBuild والـRun كل مرة، بنقدر نشغّل المشروع بأمر واحد عن طريق Docker Compose.

### Makefile Commands | أوامر مختصرة

You need `make` to use these commands (on Windows you can use Git Bash or WSL).

- `make build`: Build the Docker image.
- `make run`: Run the container on port 8080.
- `make health`: Check the health endpoint.
- `make logs`: Show the container logs.
- `make stop`: Stop and remove the container.
- `make clean`: Remove the container and the image.

ضفت Makefile عشان أسهّل تشغيل المشروع، بحيث كل أمر طويل صار إله اختصار بسيط.

### Dockerfile Details | تفاصيل الـDockerfile

- **Multi-stage build:** The first stage compiles the code using the JDK, and the second stage runs it using the JRE only, so the final image is smaller.
- **Healthcheck:** Docker checks `/health` every 30 seconds. You can see the status with `docker ps`.
- **Non-root user:** The application runs as a normal user called `app`.

الـDockerfile مقسوم لمرحلتين: الأولى بتعمل Compile للكود، والثانية بتشغّله بصورة أصغر. وDocker بفحص التطبيق كل 30 ثانية، وبتظهر كلمة healthy لما يكون شغّال تمام.

### GitHub Actions | الفحص التلقائي للمشروع

Every push or pull request to `main` builds the Docker image, runs the container, and tests the home page and `/health`.

تم إضافة فحص تلقائي للمشروع للتأكد من إنه بشتغل بشكل صحيح بعد رفع أي تعديلات على المستودع. ببدأ الفحص بجلب ملفات المشروع، وبعدها ببني صورة التطبيق وبشغّله داخل حاوية، وبعدين بتأكد من عمل الصفحة الرئيسية وصفحة فحص الحالة. إذا نجحت جميع الخطوات، بتظهر علامة صح خضرا بتوضح نجاح الفحص، وبالنهاية بتم إيقاف الحاوية وحذفها.

## 6. Testing | تجربة المشروع

To test the application:

1. Add a new task.
2. Mark a task as completed.
3. Delete a task.
4. Check the updated task list.

جربت التطبيق عن طريق إضافة مهام جديدة، وتحديد مهمة كمكتملة، وحذف مهمة، والتأكد إن التغييرات بتظهر على الصفحة.

**Health Endpoint:**

http://localhost:8080/health

**Expected Response:** `OK`

## 7. Stop and Clean Up | إيقاف المشروع

**Stop the Container:**

`docker stop tasktrack-container`

**Remove the Container:**

`docker rm tasktrack-container`

**Remove the Docker Image (optional):**

`docker rmi tasktrack-java`

إذا خلصنا من التطبيق، بنقدر نوقف الـContainer، وبعدها نحذفه إذا مش محتاجينه.

## 8. Configuration | إعدادات المشروع

- **Application Port:** 8080
- **Docker Port Mapping:** 8080:8080
- **Environment Variable:** PORT (default: 8080)
- **Database:** Not required.
- **External Dependencies:** None.

المشروع ما بحتاج قاعدة بيانات أو مكتبات خارجية. المهام بتنحفظ بالذاكرة بشكل مؤقت، يعني لما نوقف التطبيق ونرجع نشغّله، المهام القديمة ما رح تضل موجودة.

## 9. Project Structure | ملفات المشروع

- `src/Main.java`: Java application code.
- `Dockerfile`: Docker build instructions.
- `docker-compose.yml`: Docker Compose file.
- `Makefile`: Short commands for Docker.
- `.dockerignore`: Files excluded from Docker build.
- `.gitignore`: Files excluded from Git.
- `.github/workflows/docker-ci.yml`: GitHub Actions workflow.
- `README.md`: Project documentation.
- `docs/notes.md`: Technical notes.
- `docs/screenshots/`: Assignment screenshots.

كل ملف إله وظيفة معينة، وهيك المشروع بيكون مرتب وسهل لأي شخص يفتحه ويفهمه.

## 10. Screenshots | صور المشروع

The screenshots of Docker build, container execution, application testing, and GitHub commits are available in:

`docs/screenshots/`