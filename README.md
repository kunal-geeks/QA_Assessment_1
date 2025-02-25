// Selenium Automation Framework for Functional Testing

# Overview
This project automates functional testing for two use cases using Selenium, Java, TestNG, and Maven. It follows the Page Object Model (POM) design and generates detailed Extent Reports.

## Features
- **POM Design**: Modular and reusable code.
- **Data-Driven Testing**: Excel-based testing.
- **Dynamic Date Handling**: Automates date selection.
- **Custom Utilities**: For Excel operations and reporting.
- **Detailed Reporting**: Extent Reports for execution results.

## Use Cases

### Use Case 1: Erail Website(https://erail.in/)
1. Automates station selection and dropdown data extraction.
2. Compares dropdown data with expected values in an Excel file.
3. Selects a date dynamically (30 days from today).

### Use Case 2: OrangeHRM Login
1. Tests valid and invalid login scenarios using data-driven testing.
2. Generates Extent Reports for results.

## Setup
1. Clone the repository.
2. Import into your IDE (e.g., IntelliJ, Eclipse).
3. Update `pom.xml` with dependencies:

4. Run test scripts using TestNG.
5. View reports in the `reports` directory.

## Logging & Reporting
- Logs critical events for debugging.
- Extent Reports include detailed execution results and screenshots.


## Author
Kunal Sharma
