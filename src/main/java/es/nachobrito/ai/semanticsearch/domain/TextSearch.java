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

package es.nachobrito.ai.semanticsearch.domain;

import java.util.List;

/**
 * @author nacho
 */
public interface TextSearch {
  record Result(String candidate, double score) {
    @Override
    public String toString() {
      return "%.2f %s".formatted(score, candidate);
    }
  }

  List<Result> filterCandidates(List<String> candidates, String query);
}
