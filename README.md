# Lab 4: ML Model Testing and Evaluation
SOFE3980U - Software Quality and Testing | Ontario Tech University

**Name:** Khushi Patel
**Student ID:** 100940709
**Subject:** Software Quality & Project Mgm
**CRN:** 75766
**Section:** 005


## Overview
This lab focuses on testing and evaluating Machine Learning models using different metrics depending on the problem type. Three tasks are covered, each in its own Maven-managed Java project.


## Task 1: Single-Variable Continuous Regression (SVCR)
Evaluates three regression models using:
- **MSE** (Mean Square Error)
- **MAE** (Mean Absolute Error)
- **MARE** (Mean Absolute Relative Error)

### Results
| Model | MSE | MAE | MARE |
|-------|-----|-----|------|
| model_1.csv | 112.09913 | 8.447413 | 0.1245 |
| model_2.csv | 102.971924 | 8.129143 | 0.1194 |
| model_3.csv | 410.53265 | 16.090715 | 0.2374 |

**Best Model: model_2.csv** (lowest MSE, MAE, and MARE)


## Task 2: Single-Variable Binary Regression (SVBR)
Evaluates three binary classification models using:
- **BCE** (Binary Cross Entropy)
- **Confusion Matrix**
- **Accuracy, Precision, Recall, F1 Score**
- **AUC-ROC**

### Results
| Model | Accuracy | Precision | Recall | F1 | AUC-ROC |
|-------|----------|-----------|--------|----|---------|
| model_1.csv | 0.8441 | 0.8459 | 0.8461 | 0.8460 | 0.9214 |
| model_2.csv | 0.8931 | 0.8992 | 0.8884 | 0.8938 | 0.9596 |
| model_3.csv | 0.9546 | 0.9555 | 0.9548 | 0.9551 | 0.9912 |

**Best Model: model_3.csv** (highest across all metrics)


## Task 3: Multiclass Classification (MCC)
Evaluates a 5-class classification model using:
- **Cross Entropy (CE)**
- **Confusion Matrix**

### Results
- **CE = 1.0077**
- Confusion matrix for 5 classes included in output


## How to Run

Each task is a separate Maven project. Navigate into the folder and run:
```bash
mvn clean package assembly:single
java -jar target/<TASK>-1.0.0-jar-with-dependencies.jar
```

Replace `<TASK>` with `SVCR`, `SVBR`, or `MCC`.


## Discussion
Accuracy, precision, and recall each measure model performance differently.

- **Accuracy:** percentage of correct predictions. Works well for balanced datasets.
- **Precision:** of all predicted positives, how many were actually positive. Important when false positives are costly (e.g. spam filters).
- **Recall:** of all actual positives, how many did the model catch. Important when false negatives are costly (e.g. cancer detection).
- **F1 Score:** balances precision and recall. Useful when you can't sacrifice either.


## Tech Stack
- Java
- Maven
- OpenCSV