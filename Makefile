init:
	git submodule update --init --recursive
top: init
	mill -i chipv.runMain chipv.topGcd 
gcdtest: init
	mill -i chipv.test -z "Gcd"


all: init
	mill -i chipv
alltest: init
	mill -i chipv.test
