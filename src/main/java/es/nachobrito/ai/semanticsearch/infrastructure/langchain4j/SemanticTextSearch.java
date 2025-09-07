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

package es.nachobrito.ai.semanticsearch.infrastructure.langchain4j;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15.BgeSmallEnV15EmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;
import es.nachobrito.ai.semanticsearch.domain.TextSearch;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author nacho
 */
public class SemanticTextSearch implements TextSearch {
  private EmbeddingModel embeddingModel;

  public SemanticTextSearch() {
    getEmbeddingModel();
  }

  @Override
  public List<Result> filterCandidates(List<String> candidates, String query) {
    var embeddings = createEmbeddings(candidates);
    var queryEmbedding = getEmbeddingModel().embed(query).content();
    return embeddings.entrySet().stream()
        .map(
            entry -> {
              var score = CosineSimilarity.between(entry.getValue(), queryEmbedding);
              return new Result(entry.getKey(), score);
            })
        .sorted(Comparator.comparingDouble(Result::score).reversed())
        .toList();
  }

  private Map<String, Embedding> createEmbeddings(List<String> candidates) {
    return candidates.stream()
        .collect(
            Collectors.toMap(
                Function.identity(), candidate -> getEmbeddingModel().embed(candidate).content()));
  }

  private EmbeddingModel getEmbeddingModel() {
    if (embeddingModel == null) {
      embeddingModel = new BgeSmallEnV15EmbeddingModel();
    }
    return embeddingModel;
  }
}
