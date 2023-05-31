all: init
	mill -i chipv
alltest: init
	mill -i chipv.test
init:
	git submodule update --init --recursive
gcd: init
	mill -i chipv.runMain chipv.top.topGcd
gcdtest: init
	mill -i chipv.test -z "Gcd"


