package com.theta360.gui;

import com.theta360.ptp.PtpException;
import com.theta360.ptp.type.UINT32;
import com.theta360.theta.Theta;
import com.theta360.theta.ThetaEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ThetaGUI extends JFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThetaGUI.class);

    public static final UINT32 SESSION_ID = new UINT32(1);

    public static void main(String[] args) {
        JFrame thetaGUI = new ThetaGUI();
        thetaGUI.setVisible(true);
    }

    private static ThetaEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(UINT32 objectHandle) {
            LOGGER.info("onObjectAdded: " + objectHandle);
        }

        @Override
        public void onDevicePropChanged(UINT32 devicePropCode) {
            LOGGER.info("onDevicePropChanged: " + devicePropCode);
        }

        @Override
        public void onStoreFull(UINT32 storageID) {
            LOGGER.info("onStoreFull: " + storageID);
        }

        @Override
        public void onCaptureComplete(UINT32 transactionID) {
            LOGGER.info("onCaptureComplete: " + transactionID);
        }
    };

    private JButton captureButton = new JButton() {
        {
            setText("capture");
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    try (Theta theta = new Theta()) {
                        theta.addListener(listener);
                        theta.getDeviceInfo();
                        theta.initiateCapture();
                    } catch (IOException | PtpException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            });
        }
    };

    public ThetaGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(200, 200);
        getContentPane().add(captureButton);
    }
}
