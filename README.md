#SR Crawler

## Usage 
Run with default arguments: `mvn clean install exec:java`

Run with  custom arguments : First compile using:  `mvn compile`
							 then package using:  `mvn clean package`.
							 Now the program will run as a Linux command, using the following pattern:
`crawl <input_json_file> <output_file>`


## Schemas 
The input and output POJOs are generated with `mvn jsonschema2pojo:generate`. 

## Useful links
https://jsoup.org/cookbook/extracting-data/selector-syntax

## Notes
If you are using Intellij IDEA:
Open:

> Help -> Edit Custom VM Options...

And append the following:

```
-Dfile.encoding=UTF-8
-Dconsole.encoding=UTF-8
```