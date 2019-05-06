library("jsonlite", quietly=T, verbose=F, warn.conflicts=FALSE)
library("ggplot2",quietly=T, verbose=F, warn.conflicts=FALSE)
library("plyr", quietly=T, verbose=F, warn.conflicts=FALSE)
source("functions.R")
source("plot.R")
source("constants.R")

args <- commandArgs(trailingOnly = TRUE)
configPath <- args[1]

results <-read.csv2(resultsPath, header=TRUE, row.names = NULL)
results$Model = as.factor(results$Model)

config <- fromJSON(configPath)

if (validPhase(results, config$Summarize_Functions$Phases) == FALSE){
  print("Non existing phasename provided!")
  quit()
}
  
index <- 0
settings <- PlotSettings()
uniqueScenarios <- unique(results$Model)

for(row in 1:nrow(config$Summarize_Functions)){
  phases <- config$Summarize_Functions[row,]$Phases
  name <- config$Summarize_Functions[row,]$Name
  index <- index + 1
  for(scenario in uniqueScenarios){
    metric <- "Time"
    subData1 <- subset(results, Model==scenario & MetricName == metric)
    subData1$MetricValue <- as.numeric(as.character(subData1$MetricValue)) * (10**-6)
    
    if (config$Dimensions$Groups$View){
      uniqueTools <- unique(subData1$Tool)
      settings <- setGroup(settings, "View")
      for(tool in uniqueTools){
        subData2 <- subset(subData1, Tool==tool)
        
        if (config$Dimensions$X_Dimensions$Model){
          title <- paste(tool, ", ", scenario, ", Function: ", concatPhases(phases), sep='')
         
          settings <- setTitle(settings, title)
          settings <- setDimensions(settings, "Model", "MetricValue")
          settings <- setLabels(settings, "Model", "Time (ms)")
          settings <- setAxis(settings, "Factor", yAxis)
          for (extension in config$Extension){
            fileName <- paste(rootPath, scenario, "-", tool, "-GroupBy-View-", metric, "-", name, ".",  extension, sep='')
            savePlot(subData2, settings, phases, fileName)
          }
          write.csv(subData2, file = paste(rootPath, scenario, "-", tool, "-GroupBy-View-", metric, "-", name, ".csv", sep=''))
        }
        
        if (config$Dimensions$X_Dimensions$Iteration){
          uniqueModels <-unique(subData2$Model)
          for(changeSet in uniqueModels){
            subData3 <- subset(subData2, Model==changeSet)
            title <- paste(tool, ", Model: ", changeSet, ", Function: ", concatPhases(phases), sep='')
           
            settings <- setTitle(settings, title)
            settings <- setDimensions(settings, "Iteration", "MetricValue")
            settings <- setLabels(settings, "Iterations", "Time (ms)")
            settings <- setAxis(settings, "Continuous", yAxis)
            for (extension in config$Extension){
              fileName <- paste(rootPath, scenario, "-", tool, "-changeSet-", changeSet, "-GroupBy-View-", metric, "-", name, ".", extension, sep='')
              savePlot(subData3, settings, phases, fileName)
            }
            write.csv(subData3, file = paste(rootPath, scenario, "-", tool, "-Model-", changeSet, "-GroupBy-View-", metric, "-", name, ".csv", sep=''))
          }
        } 
      }
    }
  }
  
  if (config$Dimensions$Groups$Tool){
    metric <- "Time"
    subData1 <- subset(results, MetricName == metric)
    subData1$MetricValue <- as.numeric(as.character(subData1$MetricValue)) * (10**-6)
    
    uniqueViews <- unique(subData1$View)
    settings <- setGroup(settings, "Tool")
    for(view in uniqueViews){
      subData2 <- subset(subData1, View==view)
      
      if (config$Dimensions$X_Dimensions$Model){
        title <- paste(view, ", Function: ", concatPhases(phases), sep='')
        settings <- setTitle(settings, title)
        settings <- setDimensions(settings, "Model", "MetricValue")
        settings <- setLabels(settings, "Model", "Time (ms)")
        settings <- setAxis(settings, "Discrete", yAxis)
        for (extension in config$Extension){
          fileName <- paste(rootPath, view, "-GroupBy-Tool-",metric, "-", name, ".", extension, sep='')
          savePlot(subData2, settings, phases, fileName)
        }
        write.csv(ddply(subData2, c("Tool", "Model"), summarise, N=length(MetricValue), mean=mean(MetricValue), sd=sd(MetricValue)), file = paste(rootPath, view, "-GroupBy-Tool-",metric, "-", name, ".csv", sep=''))
      }
      
      if (config$Dimensions$X_Dimensions$Iteration){
        uniqueSizes <-unique(subData2$Model)
        settings <- setDimensions(settings, "Iteration", "MetricValue")
        settings <- setLabels(settings, "Iterations", "Time (ms)")
        settings <- setAxis(settings, "Continuous", yAxis)
        for(size in uniqueSizes){
          subData3 <- subset(subData2, Model==size)
          title <- paste(view, ", Model: ", size, ", Function:  ", concatPhases(phases), sep='')
          for (extension in config$Extension){
            fileName <- paste(rootPath, view, "-Model-", size, "-GroupBy-Tool-", metric, "-", name, ".", extension, sep='')
            settings <- setTitle(settings, title)
            savePlot(subData3, settings, phases, fileName)
          }
          write.csv(subData3, file = paste(rootPath, view, "-Model-", size, "-GroupBy-Tool-", metric, "-", name, ".csv", sep=''))
        }
      }     
    }
  }
}
