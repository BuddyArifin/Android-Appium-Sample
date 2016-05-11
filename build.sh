#!/bin/sh
# This is the initial commit for the build.sh file

usage() {
    echo "Usage: 'basename $0'"
    echo "  -p  |  --platform            Set device or emulator to run the test"
    echo "  -t  |  --command-timeout     The default command timeout for the server to use for all sessions. Will still be overridden by newCommandTimeout cap"
    echo "  -s  |  --session-override    Enables session override (clobbering)"
    echo "  -lt |  --launch-timeout      how long in ms to wait for Instruments to launch"
    echo "  -c  |  --command             command start or stop appium server "
}

#parsing input parameters
PROJECT_NAME=Morph-ios-appium-automation
DISPLAY=:0.0

STATUS_SERVER=""
PLATFORM="emulator"
COMMAND_TIMEOUT="9000"
LAUNCH_TIMEOUT="240000"
SESSION_OVERRIDE="true"
UDID=""
WORKSPACE=""

while [ "$#" != 0 ]; do
    PARAM=$1
    shift

    case $PARAM in
        -h | --help)
            usage
            exit
            ;;
        -p | --platform)
            PLATFORM=$1
            ;;
        -t | --command-timeout)
            COMMAND_TIMEOUT=$1
            ;;
        -s | --session-override)
            SESSION_OVERRIDE=$1
            ;;
        -lt | --launch-timeout)
            LAUNCH_TIMEOUT=$1
            ;;
        -c  | --command)
            STATUS_SERVER=$1
            ;;
    *)
        echo "ERROR: unknown parameter \"$PARAM\""
        usage
        exit 1
        ;;
    esac
    shift
done


# Echoing parameters
echo "Running build:"
echo "platform = $PLATFORM"
echo "command timeout = $COMMAND_TIMEOUT"
echo "launch app timeout = $LAUNCH_TIMEOUT"
echo "session override = $SESSION_OVERRIDE"

#capturing UDID devices
UDID=$(system_profiler SPUSBDataType | grep "Serial Number: " | tail -c 41)

if [ "$STATUS_SERVER" == "start" ]; then
    if [ "$PLATFORM" == "emulator" ]; then
        echo Start up...
        nohup appium --native-instruments-lib --session-override > appium-test.log 2>&1 &
        echo "PID process on" $!
        echo $! > appium.pid
        sleep 50
    elif [ "$PLATFORM" == "device" ] && [ "$UDID" != "" ]; then
        nohup appium -U $UDID --debug-log-spacing \
        --show-ios-log --native-instruments-lib > appium-test.log 2>&1 &
        echo $! > appium.pid
        sleep 50
    fi

elif [ "$STATUS_SERVER" == "stop" ]; then
    echo Shutting down...
    kill -9 $( cat appium.pid )
 fi