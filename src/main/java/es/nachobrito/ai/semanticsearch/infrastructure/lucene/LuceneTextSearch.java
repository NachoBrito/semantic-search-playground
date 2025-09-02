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

package es.nachobrito.ai.semanticsearch.infrastructure.lucene;

import es.nachobrito.ai.semanticsearch.domain.TextSearch;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

/**
 * @author nacho
 */
public class LuceneTextSearch implements TextSearch {
  private static final String FIELD_DESCRIPTION = "description";

  private static final Analyzer analyzer = new EnglishAnalyzer();
  private static final QueryParser parser = new QueryParser(FIELD_DESCRIPTION, analyzer);

  @Override
  public List<Result> filterCandidates(List<String> candidates, String query) {
    var indices = buildIndices(candidates);
    Query parsedQuery = getParsedQuery(query);
    return indices.entrySet().stream()
        .map(
            entry -> {
              var score = entry.getValue().search(parsedQuery);
              return new Result(entry.getKey(), score);
            })
        .sorted(Comparator.comparingDouble(Result::score).reversed())
        .toList();
  }

  private static Query getParsedQuery(String query) {
    Query parsedQuery = null;
    try {
      parsedQuery = parser.parse(query);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return parsedQuery;
  }

  private Map<String, MemoryIndex> buildIndices(List<String> candidates) {
    return candidates.stream()
        .collect(
            Collectors.toMap(
                Function.identity(),
                candidate -> {
                  var idx = new MemoryIndex();
                  idx.addField(FIELD_DESCRIPTION, candidate, analyzer);
                  return idx;
                }));
  }
}
