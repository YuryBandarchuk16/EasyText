✉️📖This application updates some words from the text using synonyms. It makes the text much more easier to read and understand (Demonstration GIF below).

For example, you are reading a book. Some words are really difficult. But you don't want to translate, you just want to know
their synonyms. This app will help you!

Sometimes it does not work correctly, but usually it replaces the words with their synonyms well.

The algortithm is the following:

 - The application has two files: a file of synonyms and a file with the most common words (nearly about 30 000 words).
  
 - After starting the application all words are added to HashMap. (Firstly it was Trie Data Structure, but after some tests I figured out that in average HashMap works faster).

 - Iterating over the words from the input. Then for each word if we have a synonym in local base just replace it. 
  
 - Otherwise making a request to Thesaurus dictionary using its API, which returns a list of synonyms. Take the synonym which has the largest frequency rating and make a pair of synonyms (add to HashMap, to file with synonyms and etc.)
 
Also you can manually add pairs of synonyms.


📝Sample input:
  plethora
  accretion
  pulchritude
  accost
  
📝Sample output:
  excess
  increase
  beauty
  address
  
![](https://github.com/YuryBandarchuk16/EasyText/blob/master/demonstration.gif)
