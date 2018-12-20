# communications_retriever

Current solution being developed by Luis Lopes

Exercise from link https://github.com/vas-test/test1 

The goal of this exercise is to create a service which exposes an API through which we will retrieve information related with certain Mobile Communication Platform.


The service has an HTTP endpoint ("/YYYMMDD") that receives a date parameter, and loads from an Url a file with communications's data relative to a specific date, in JSON like format. 
Also processes the file to a JSON array and stores it locally.


The service has a second HTTP endpoint ("/metrics") that returns a set of counters related with the processed JSON file:

- Number of rows with missing fields
- Number of messages with blank content
- Number of rows with fields errors
- Number of calls origin/destination grouped by country code 
- Relationship between OK/KO calls
- Average call duration grouped by country code 
- Word occurrence ranking for the given words in message_content field.


The service has a third HTTP endpoint ("/kpis") that returns a set of counters related with the service:

- Total number of processed JSON files
- Total number of rows
- Total number of calls
- Total number of messages
- Total number of different origin country codes
- Total number of different destination country codes Duration of each JSON process
