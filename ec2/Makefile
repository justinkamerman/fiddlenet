WRITE-MIME_MULTIPART=/usr/lib/cloud-init/write-mime-multipart

.PHONY: clean

cloud-config.txt: ubuntu-config.txt hadoop-setup.sh
	$(WRITE-MIME_MULTIPART) --output=$@ $^

clean:
	$(RM) cloud-config.txt