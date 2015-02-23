package org.theta4j.sample;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.Theta;
import org.theta4j.ThetaEventListener;
import org.theta4j.ThetaException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

public final class ThetaCLI {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThetaCLI.class);

    private static long objectHandle;
    private static final CountDownLatch waitObjectAdded = new CountDownLatch(1);

    private ThetaCLI() {
    }

    private static ThetaEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(long objectHandle) {
            LOGGER.info("onObjectAdded: " + objectHandle);
            ThetaCLI.objectHandle = objectHandle;
            waitObjectAdded.countDown();
        }

        @Override
        public void onCaptureStatusChanged() {
            LOGGER.info("onCaptureStatusChanged");
        }

        @Override
        public void onRecordingTimeChanged() {
            LOGGER.info("onRecordingTimeChanged");
        }

        @Override
        public void onRemainingRecordingTimeChanged() {
            LOGGER.info("onRemainingRecordingTimeChanged");
        }

        @Override
        public void onStoreFull(long storageID) {
            LOGGER.info("onStoreFull: " + storageID);
        }

        @Override
        public void onCaptureComplete(long transactionID) {
            LOGGER.info("onCaptureComplete: " + transactionID);
        }
    };

    private static CommandLine parseArgs(String[] args) throws ParseException {
        Options options = new Options();

        // output
        Option o = new Option("o", true, "File name of captured image.");
        o.setLongOpt("output");
        o.setType(String.class);
        options.addOption(o);

        // time
        Option t = new Option("t", false, "Get the date time of RICOH THETA.");
        o.setLongOpt("datetime");
        options.addOption(t);

        CommandLineParser parser = new PosixParser();
        return parser.parse(options, args);
    }

    public static void main(String[] args) throws IOException, ThetaException, ParseException, InterruptedException {
        CommandLine cmd = parseArgs(args);

        try (Theta theta = new Theta()) {
            theta.addListener(listener);
            theta.getDeviceInfo();

            if (cmd.hasOption("t")) {
                System.out.println(theta.getDateTime());
                return;
            }

            theta.initiateCapture();

            if (!cmd.hasOption("o")) {
                return;
            }

            waitObjectAdded.await();

            File file = new File(cmd.getOptionValue("o"));
            try (OutputStream output = new FileOutputStream(file)) {
                theta.getResizedImageObject(objectHandle, output);
            }
        }
    }
}
