source("theme.R")

savePlot <-function(results, settings, fileName){
   merged = results
  if (settings@xDimension == "Model"){
    # summarise the iterations
    data <- ddply(merged, c("Model", "Tool", "RunIndex", "MetricName"),
                  summarize, MetricValue=sum(MetricValue))
    data <- ddply(data, c("Model", "Tool", "MetricName"),
                  summarize, MetricValue=mean(MetricValue))
  }
  else if(settings@xDimension == "Iteration"){
    data <- ddply(merged, c("Tool", "Model", "RunIndex", "MetricName", "Iteration"),
                  summarize, MetricValue=sum(MetricValue))
    data <- ddply(data, c("Tool", "Model", "MetricName", "Iteration"),
                  summarize, MetricValue=median(MetricValue))
  }
  else {
    return()
  }
  artifacts <- unique(data[[settings@xDimension]])
  minValue <- min(data$MetricValue)
  maxValue <- max(data$MetricValue)

  xLabels <- c(artifacts)
  plot <- ggplot(data,aes_string(x = settings@xDimension, y = settings@yDimension)) +
    geom_line(aes_string(group = settings@group, colour=settings@group), size=lineSize) + 
    geom_point(aes_string(shape = settings@group, colour=settings@group), size=pointSize) +
    scale_shape_manual(values=1:nlevels(data[[settings@group]])) +
    ylab(settings@yLabel) +
    xlab(settings@xLabel) +
    ggtitle(label = settings@title) +
    bwTheme
  
  if (settings@xAxis == "Continuous"){
    plot <- plot + scale_x_continuous(breaks = c(artifacts))
  }
  else if (settings@xAxis == "Log2"){
    plot <- plot + scale_x_log10(breaks = c(artifacts), labels = xLabels)
  }
  if (minValue == 0){
    print("The minimum metricvalue equals with 0. The plot cannot be generated.")
    return()
  }
  if (settings@yAxis == "Continuous"){
    plot <- plot + scale_y_continuous(breaks = seq(minValue, maxValue, by=round(maxValue/5,0)),
                                      labels = seq(minValue, maxValue, by=round(maxValue/5,0)))
  }
  else if (settings@yAxis == "Log10"){
    plot <- plot + scale_y_log10(breaks = 10^seq(round(log10(minValue)), round(log10(maxValue)), by=1), 
                                labels = 10^seq(round(log10(minValue)), round(log10(maxValue)), by=1))
  }
  ggsave(plot,filename = fileName, width=10, height=5, dpi=192)
  print(fileName)


}

createFolders <- function(rootPath, subFolders){
  if (file.exists(rootPath) == FALSE){
    dir.create(rootPath)
  }
  for (folder in subFolders){
    path <- paste(rootPath, folder, sep='')
    if (file.exists(path) == FALSE){
      dir.create(path)
    }
  }
}

getXLabels <- function(artifacts){
  ticks <- c()
  for(size in artifacts){
    ticks <- c(ticks, labels[[as.character(size)]])
  }
  return(ticks)
}
