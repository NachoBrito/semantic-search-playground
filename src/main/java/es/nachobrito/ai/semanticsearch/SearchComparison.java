/*
 *    Copyright 2025 Nacho Brito
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package es.nachobrito.ai.semanticsearch;

import es.nachobrito.ai.semanticsearch.domain.TextSearch;
import es.nachobrito.ai.semanticsearch.infrastructure.langchain4j.SemanticTextSearch;
import es.nachobrito.ai.semanticsearch.infrastructure.lucene.LuceneTextSearch;
import java.util.List;

public class SearchComparison {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Usage: SemanticSearchToolbox <query>");
      System.exit(1);
    }
    List<? extends TextSearch> searchers =
        List.of(new LuceneTextSearch(), new SemanticTextSearch());

    var candidates =
        List.of(
            "What is the best time to plant rice?",
            "How often should I water tomato plants?",
            "What fertilizer is good for wheat crops?",
            "How can I control pests on my cotton farm?",
            "Which crop is best for sandy soil?",
            "Is there a difference between four-wheel drive and all-wheel drive?");

    var query = args[0];
    System.out.printf("\nCandidates for query \"%s\"\n\n", query);
    for (TextSearch searcher : searchers) {
      System.out.printf("Searching with \"%s\"\n", searcher.getClass().getSimpleName());
      searcher.filterCandidates(candidates, query).forEach(System.out::println);
      System.out.println("---");
    }
  }
}
