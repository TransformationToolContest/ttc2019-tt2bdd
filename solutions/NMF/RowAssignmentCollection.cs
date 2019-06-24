using NMF.Collections.ObjectModel;
using NMF.Expressions.Linq;
using System;
using System.Collections.Generic;
using System.Text;
using TTC2019.BinaryDecision.Metamodels.TruthTables.TT;

namespace TTC2019.BinaryDecision
{

    internal class RowAssignmentCollection : CustomCollection<RowAssignment>
    {
        private readonly IRow _row;

        public RowAssignmentCollection(IRow row) : base(row.Cells.Where(c => c.Port is IInputPort)
                                                                 .Select(c => new RowAssignment(c.Port as IInputPort, c.Value)))
        {
            _row = row;
        }

        public override void Add(RowAssignment item)
        {
            if (item != null)
            {
                _row.Cells.Add(new Cell()
                {
                    Port = item.Port,
                    Value = item.Value
                });
            }
        }

        public override void Clear()
        {
            for (int i = _row.Cells.Count - 1; i >= 0; i--)
            {
                if (_row.Cells[i].Port is IInputPort)
                {
                    _row.Cells.RemoveAt(i);
                }
            }
        }

        public override bool Remove(RowAssignment item)
        {
            var cell = _row.Cells.FirstOrDefault(c => c.Port == item.Port);
            return cell != null && _row.Cells.Remove(cell);
        }
    }
}
