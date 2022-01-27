# This is a regular comment, that will not be displayed

## ----------------------------------------------------------------------
## This is a help comment. This Makefile supports the deployment and execution of the ArrowFabric components
##                                    ______    _          _
##       /\                          |  ____|  | |        (_)
##      /  \   _ __ _ __ _____      _| |__ __ _| |__  _ __ _  ___
##     / /\ \ | '__| '__/ _ \ \ /\ / /  __/ _` | '_ \| '__| |/ __|
##    / ____ \| |  | | | (_) \ V  V /| | | (_| | |_) | |  | | (__
##   /_/    \_\_|  |_|  \___/ \_/\_/ |_|  \__,_|_.__/|_|  |_|\___|
##
## ----------------------------------------------------------------------
ROOT_DIR:=$(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
help:		## Show this help.
	@sed -ne '/@sed/!s/## //p' $(MAKEFILE_LIST)

list-nodes:	## List available nodes
	@/opt/pbs/bin/qsub $(ROOT_DIR)/listnodes.sh
	@echo "Nodes discovered: "
	@cat /home/nabhan/arrowfabric/.ci/srvrs.txt
	@rm /home/nabhan/arrowfabric/.ci/srvrs.txt

connect:   ## connect to node
	qsub -I -l walltime=00:30:00,host=$(node)

start-dispatcher:
	java -cp $(ROOT_DIR)/../target/ArrowFabric-1.0-SNAPSHOT-jar-with-dependencies.jar dispatcher.FabricDispatcher