expected = read.csv2("../expected-results/results.csv")
actual = subset(read.csv2("../output/output.csv"), MetricName=="Elements")

tools = unique(actual$Tool)

for (tool in tools) {
  tool_data = subset(actual, Tool==tool)
  queries = unique(tool_data$View)
  for (query in queries) {
    query_data = subset(tool_data, View==query)
    query_n = length(row.names(query_data))
    for (i in 1:query_n) {
      query.row = query_data[i,]
      expected.row = subset(expected, ChangeSet==query.row$ChangeSet & View==query & Iteration==query.row$Iteration)
      
      if (length(as.character(expected.row$MetricValue)) > 0) {
        if (as.character(query.row$MetricValue) != as.character(expected.row$MetricValue)) {
          print(paste(tool, "is wrong. Was ", query.row$MetricValue, "but expected", expected.row$MetricValue, "for change set", query.row$ChangeSet, "query", query, "iteration", query.row$Iteration))
        }
      } else {
        print(paste("Warning:", tool, "produced the result", query.row$MetricValue, "but expected result is unavailable for change set", query.row$ChangeSet, "query", query, "iteration", query.row$Iteration))
      }
    }
  }
}
