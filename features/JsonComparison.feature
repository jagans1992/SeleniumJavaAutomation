#Create folder in below path and provide the folder name so that Json files and report will be stored
#path: //src//test//resources//testdata//jsonComparison//

@regression
Scenario Outline: Get and Verify API response
And User get the Epic API response for "<MARSHA>" by hitting EndPoint "Epic" and store the json "jsonComparison"
And User get the Catalog API response for "<MARSHA>" by hitting EndPoint "Catalog" and store the json "jsonComparison"
And User verify "<MARSHA>" form Epic "Epic" and Catalog "Catalog" json and generate the report "jsonComparison"

Examples:
|MARSHA|
|NAMEC|
|JAGAH|


#Provide the folder Name so that all the excel files reports will be combined and stroed
#path: //src//test//resources//testdata//jsonComparison//

@regression
Scenario: Generate Final Excel Report for all the Marsha compared for catalog and Epic API response
And User generate the Final Report "jsonComparison"