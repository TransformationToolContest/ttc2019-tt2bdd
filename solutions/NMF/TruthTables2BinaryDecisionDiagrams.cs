using NMF.Synchronizations;
using NMF.Expressions;
using NMF.Expressions.Linq;
using System;
using System.Collections.Generic;
using System.Text;
using TTC2019.BinaryDecision.Metamodels.BinaryDecisionDiagrams.BDD;
using TTC2019.BinaryDecision.Metamodels.TruthTables.TT;
using NMF.Models;
using NMF.Collections.ObjectModel;
using System.Linq;

namespace TTC2019.BinaryDecision
{
    class TruthTables2BinaryDecisionDiagrams : ReflectiveSynchronization
    {        
        public class TT2BDD : SynchronizationRule<TruthTable, BDD>
        {
            public override void DeclareSynchronization()
            {
                Synchronize(tt => tt.Name, bdd => bdd.Name);

                SynchronizeMany(SyncRule<Port2Port>(),
                    tt => tt.Ports,
                    bdd => bdd.Ports);

                SynchronizeMany(SyncRule<Row2Leaf>(),
                    tt => tt.Rows,
                    bdd => new BDDLeafCollection(bdd));
            }
        }

        public class Row2Leaf : SynchronizationRule<IRow, ILeaf>
        {
            public override void DeclareSynchronization()
            {
                SynchronizeMany(SyncRule<OutputCell2Assignment>(),
                    row => row.Cells.Where(cell => cell.Port is Metamodels.TruthTables.TT.IOutputPort),
                    leaf => leaf.Assignments);

                SynchronizeMany(SyncRule<RowAssignment2TreeAssignment>(),
                    row => new RowAssignmentCollection(row),
                    leaf => new TreeAssignmentsCollection(leaf));
            }
        }

        public class RowAssignment2TreeAssignment : SynchronizationRule<RowAssignment, TreeAssignment>
        {
            public override void DeclareSynchronization()
            {
                Synchronize(SyncRule<InputPort2InputPort>(), assignment => assignment.Port, assignment => assignment.Port);
                Synchronize(assignment => assignment.Value, assignment => assignment.Value);
            }
        }

        public class Port2Port : SynchronizationRule<Metamodels.TruthTables.TT.IPort, Metamodels.BinaryDecisionDiagrams.BDD.IPort>
        {
            public override void DeclareSynchronization()
            {
                Synchronize(port => port.Name, port => port.Name);
            }
        }

        public class InputPort2InputPort : SynchronizationRule<Metamodels.TruthTables.TT.IInputPort, Metamodels.BinaryDecisionDiagrams.BDD.IInputPort>
        {
            public override void DeclareSynchronization()
            {
                MarkInstantiatingFor(SyncRule<Port2Port>());
            }
        }

        public class OutputPort2OutputPort : SynchronizationRule<Metamodels.TruthTables.TT.IOutputPort, Metamodels.BinaryDecisionDiagrams.BDD.IOutputPort>
        {
            public override void DeclareSynchronization()
            {
                MarkInstantiatingFor(SyncRule<Port2Port>());
            }
        }

        public class OutputCell2Assignment : SynchronizationRule<ICell, IAssignment>
        {
            public override void DeclareSynchronization()
            {
                Synchronize(cell => cell.Value, assignment => assignment.Value);

                Synchronize(SyncRule<OutputPort2OutputPort>(),
                    cell => cell.Port as Metamodels.TruthTables.TT.IOutputPort,
                    assignment => assignment.Port);
            }
        }
    }
}
