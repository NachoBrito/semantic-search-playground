# Semantic Search playground

This project is a simple tool to compare results of keyword based search and semantic search. It is the companion code
of my article ["How Semantic Search Works"](https://www.nachobrito.es/artificial-intelligence/semantic-search/).

This tool will accept a query string as parameter, and perform a search among a list of candidate strings using both a
keyword based search (powered by [Lucene](https://lucene.apache.org/core/)) and a semantic search, powered by
[LangChain4J embeddings](https://github.com/langchain4j/langchain4j-embeddings).

## Usage

First, build the tool:

```shel
mvn clean compile assembly:single
```

Then, run a query:

```shell
 java -jar target/semantic-search-playground-1.0-jar-with-dependencies.jar "YOUR QUERY"
 ```

For example:

```shell
$ java -jar target/semantic-search-playground-1.0-jar-with-dependencies.jar "We want to avoid insects"

Candidates for query "We want to avoid insects"

Searching with "LuceneTextSearch"
0.00 Which crop is best for sandy soil?
0.00 How can I control pests on my cotton farm?
0.00 How often should I water tomato plants?
0.00 What fertilizer is good for wheat crops?
0.00 What is the best time to plant rice?
0.00 Is there a difference between four-wheel drive and all-wheel drive?
---
Searching with "SemanticTextSearch"
0.72 How can I control pests on my cotton farm?
0.54 What is the best time to plant rice?
0.54 Which crop is best for sandy soil?
0.50 What fertilizer is good for wheat crops?
0.50 How often should I water tomato plants?
0.41 Is there a difference between four-wheel drive and all-wheel drive?
---
```