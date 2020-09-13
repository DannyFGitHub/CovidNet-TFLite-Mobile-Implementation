# Covid Mobile Classifier App
## CovidNet TFLite Model Android Mobile Classifier Implementation

__WARNING: Do not use this application as a method of self-diagnosis. Please visit a doctor for an official diagnosis.__

<p align="center">
  <img width="460" src="screenshots/screenshot01.png">
</p>

Both Model A and Model B have been trained to identifiy: Pneumonia, COVID-19 and Normal Lungs X Ray.


The models used in this application can be found at:
https://github.com/DannyFGitHub/pneumoCheck-Models-TFLite-COVID-Net


### TFLite Version for mobile - COVIDNet Chest X-Ray Classification

_Currently I have converted the CXR4 models A and B to TFLite with no optimisation, Default Optimisation and 16Float Optimisation._

|  Type  | Input Resolution | COVID-19 Sensitivity | Optimisation | Size |       Model      |
|:------:|:----------------:|:--------------------:|:------------:|:----:|:----------------:|
| TFlite |      480x480     |         95.0         |   Default    |  40M | [covidnet_a.tflite](https://drive.google.com/file/d/1_DWDkJgFnP_EtvWMMA4FdZBvxLj48T-y/view?usp=sharing)|
| TFlite |      480x480     |         93.0         |   Default    |  12M | [covidnet_b.tflite](https://drive.google.com/file/d/1lUQfmPN1KLXBkGfmPUejFCsAP10zWqkQ/view?usp=sharing)|

<br>

These models were converted from the following checkpoint unfrozen graph models:

<br>

#### ORIGINAL COVIDNet Chest X-Ray Classification
|  Type | Input Resolution | COVID-19 Sensitivity | Accuracy | # Params (M) | MACs (G) |        Model        |
|:-----:|:----------------:|:--------------------:|:--------:|:------------:|:--------:|:-------------------:|
|  ckpt |      480x480     |         95.0         |   94.3   |      40.2    |  23.63   |[COVIDNet-CXR4-A](https://bit.ly/COVIDNet-CXR4-A)|
|  ckpt |      480x480     |         93.0         |   93.7   |      11.7    |   7.50   |[COVIDNet-CXR4-B](https://bit.ly/COVIDNet-CXR4-B)|

<br><Br>

---

For a demo on implementing your own models:
https://github.com/vasugargofficial/Image-Classification-Mobilenet-AndroidDemo
