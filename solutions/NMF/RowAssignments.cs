using System;
using System.Collections.Generic;
using System.Text;
using TTC2019.BinaryDecision.Metamodels.TruthTables.TT;

namespace TTC2019.BinaryDecision
{
    class RowAssignment
    {
        public RowAssignment() { }

        public RowAssignment(IInputPort port, bool value)
        {
            Port = port;
            Value = value;
        }

        public IInputPort Port { get; set; }

        public bool Value { get; set; }
    }
}
