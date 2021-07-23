.PHONY: default
default: kas 
	
kas:
	git clone https://github.com/siemens/kas.git

install: kas	
	sudo pip install kas
