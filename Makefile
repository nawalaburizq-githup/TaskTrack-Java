IMAGE     = tasktrack-java
CONTAINER = tasktrack-container
PORT      = 8080

.PHONY: build run stop logs health clean

build:
	docker build -t $(IMAGE) .

run:
	docker run -d --name $(CONTAINER) -p $(PORT):8080 $(IMAGE)

stop:
	docker stop $(CONTAINER)
	docker rm $(CONTAINER)

logs:
	docker logs $(CONTAINER)

health:
	curl --fail http://localhost:$(PORT)/health

clean:
	-docker rm -f $(CONTAINER)
	-docker rmi $(IMAGE)
	