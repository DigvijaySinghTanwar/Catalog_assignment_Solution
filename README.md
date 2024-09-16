# Catalog_assignment_Solution
Catalog Assignment Solution :

There are two ways to run the code:

1. Using a JSON file:

Save the code: Save the provided code as a file named ShamirSecretSharing.java.
Create a JSON file: Create a text file named input.json and paste the desired JSON test case into it. For example, you can use the second test case you provided:
JSON
{
  "keys": {
    "n": 4,
    "k": 3
  },
  "1": {
    "base": "10",
    "value": "4"
  },
  "2": {
    "base": "2",
    "value": "111"
  },
  "3": {
    "base": "10",
    "value": "12"
  },
  "6": {
    "base": "4",
    "value": "213"
  }
}
Use code with caution.

Open a terminal: Open a command prompt or terminal window and navigate to the directory containing both the ShamirSecretSharing.java file and the input.json file.
Run the command: Execute the following command:
java ShamirSecretSharing input.json
This will read the JSON data from the file, calculate the constant term using the first k roots, and print the result.

2. Using command-line input:

Save the code: Save the provided code as a file named ShamirSecretSharing.java.
Open a terminal: Open a command prompt or terminal window and navigate to the directory containing the ShamirSecretSharing.java file.
Run the command: Execute the following command without any arguments:
java ShamirSecretSharing
This will prompt you to enter the JSON data directly in the terminal. Paste your desired JSON test case (e.g., the second test case) and press Enter. The program will then calculate and print the constant term for you.
