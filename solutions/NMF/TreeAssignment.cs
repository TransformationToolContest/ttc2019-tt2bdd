using System;
using System.Collections.Generic;
using System.Text;
using TTC2019.BinaryDecision.Metamodels.BinaryDecisionDiagrams.BDD;

namespace TTC2019.BinaryDecision
{
    class TreeAssignment
    {
        public TreeAssignment() { }

        public TreeAssignment(IInputPort port, bool value)
        {
            Port = port;
            Value = value;
        }

        public IInputPort Port { get; set; }

        public bool Value { get; set; }
    }
}
