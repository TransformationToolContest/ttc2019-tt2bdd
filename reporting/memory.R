library("ggplot2")
library("plyr")

results <- read.csv2("../output/output.csv", row.names = NULL)

testset = ddply(results, c("Tool", "RunIndex", "Model"), MetricValue=max(MetricValue))
testset = ddply(testset, c("Tool", "Model"), summarize, Memory=mean(MetricValue)/(1024*1024))

pointSize = 3
lineSize = 1

bwTheme <- theme(text=element_text(family="Helvetica", size=16),
                 panel.background = element_rect(fill="#FFFFFF"),
                 legend.direction="horizontal",
                 panel.grid.major = element_line(size=0.3, colour="#333333"),
                 panel.grid.minor = element_line(size=0.15, colour="#CCCCCC"),
                 axis.text.x = element_text(colour="black"),
                 axis.text.y = element_text(colour="black"),
                 legend.position="right")
  
  data = testset
  
  sizes <- unique(data$Model)
  minValue <- min(data$Memory)
  maxValue <- max(data$Memory)

plot <- ggplot(data, aes_string(x="Model",y="Memory"))
plot <- plot + geom_line(aes_string(group="Tool",colour="Tool"),size=lineSize)
plot <- plot + geom_point(aes_string(shape="Tool", colour="Tool"), size=pointSize)
plot <- plot + scale_shape_manual(values=1:4) + ylab("Memory (Mbyte)")
plot <- plot + xlab("Model")
plot <- plot + bwTheme
plot <- plot + scale_x_discrete()
plot <- plot + scale_y_log10(breaks = 10^seq(round(log10(minValue)), round(log10(maxValue)), by=1), 
                             labels = 10^seq(round(log10(minValue)), round(log10(maxValue)), by=1))
plot <- plot + ggtitle("working set")

ggsave(plot, filename="../diagrams/memory.pdf", width=7, height=4, dpi=192)