package com.touchmediaproductions.CovidNetTFLite;

public enum MLModels {
    MODEL_A_COVIDNET ("covidnet_a.tflite"),
    MODEL_B_COVIDNET ("covidnet_b.tflite");

    // internal state
    private String fileName;

    MLModels(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
