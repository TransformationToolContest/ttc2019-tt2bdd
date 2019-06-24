using NMF.Models;
using NMF.Models.Repository;
using NMF.Synchronizations;
using NMF.Transformations;
using System;
using System.Diagnostics;
using System.IO;
using TTC2019.BinaryDecision.Metamodels.BinaryDecisionDiagrams.BDD;
using TTC2019.BinaryDecision.Metamodels.TruthTables.TT;

[assembly:ModelMetadata("https://www.transformation-tool-contest.eu/2019/tt", "TTC2019.BinaryDecision.TruthTables.nmeta")]
[assembly:ModelMetadata("https://www.transformation-tool-contest.eu/2019/bdd", "TTC2019.BinaryDecision.BinaryDecisionDiagrams.nmeta")]

namespace TTC2019.BinaryDecision
{
    class Program
    {
        private static ModelRepository repository;

        private static string ModelPath;
        private static string RunIndex;
        private static string Tool;
        private static string Model;
        private static string Mode;

        private static Stopwatch stopwatch = new Stopwatch();
        private static TruthTable truthTable;
        private static TruthTables2BinaryDecisionDiagrams synchronization;

        static void Main(string[] args)
        {
            if (args.Length == 0)
            {
                Mode = "Batch".ToUpperInvariant();
            }
            else
            {
                Mode = args[0].ToUpperInvariant();
            }
            Initialize();
            Load();
            Initial();
        }

        static void Load()
        {
            stopwatch.Restart();
            truthTable = repository.Resolve(ModelPath).RootElements[0] as TruthTable;
            stopwatch.Stop();
            Report(BenchmarkPhase.Load);
        }

        static void Initialize()
        {
            stopwatch.Restart();
            repository = new ModelRepository();

            ModelPath = Environment.GetEnvironmentVariable(nameof(ModelPath));
            RunIndex = Environment.GetEnvironmentVariable(nameof(RunIndex));
            Tool = Environment.GetEnvironmentVariable(nameof(Tool));
            Model = Environment.GetEnvironmentVariable(nameof(Model));

            synchronization = new TruthTables2BinaryDecisionDiagrams();
            synchronization.Initialize();

            stopwatch.Stop();
            Report(BenchmarkPhase.Initialization);
        }

        static void Initial()
        {
            stopwatch.Restart();
            BDD bdd = null;
            synchronization.Synchronize(ref truthTable, ref bdd, SynchronizationDirection.LeftToRight,
                Mode == "Batch" ? ChangePropagationMode.None : ChangePropagationMode.OneWay);
            stopwatch.Stop();
            Report(BenchmarkPhase.Initial);
        }

        static void Report(BenchmarkPhase phase, string result = null)
        {
            GC.Collect();
            Console.WriteLine($"{Tool};{Model};{RunIndex};{phase};Time;{stopwatch.Elapsed.Ticks * 100}");
            Console.WriteLine($"{Tool};{Model};{RunIndex};{phase};Memory;{Environment.WorkingSet}");
            if (result != null)
            {
                Console.WriteLine($"{Tool};{Model};{RunIndex};{phase};Elements;{result}");
            }
        }
    }

    public enum BenchmarkPhase
    {
        Initialization,
        Load,
        Initial,
        Update
    }
}
