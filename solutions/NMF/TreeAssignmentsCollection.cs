using NMF.Collections.ObjectModel;
using NMF.Expressions.Linq;
using NMF.Models;
using System;
using System.Collections.Generic;
using System.Text;
using TTC2019.BinaryDecision.Metamodels.BinaryDecisionDiagrams.BDD;

namespace TTC2019.BinaryDecision
{
    internal class TreeAssignmentsCollection : CustomCollection<TreeAssignment>
    {
        private ILeaf _leaf;

        public TreeAssignmentsCollection(ILeaf leaf) : base(leaf.AncestorTree().Select(tree => 
            new TreeAssignment((tree.Parent as ISubtree).Port, (tree.Parent as ISubtree).TreeForOne == tree.Child)))
        {
            _leaf = leaf;
        }

        public override void Add(TreeAssignment item)
        {
            ITree last = _leaf;
            var current = _leaf.OwnerSubtreeForOne ?? _leaf.OwnerSubtreeForZero;

            while (current != null)
            {
                if (current.Port == item.Port)
                {
                    if ((current.TreeForOne == last && item.Value) || (current.TreeForZero == last && !item.Value))
                    {
                        return;
                    }
                    ChangeAssignment(current, item);
                }
                last = current;
                current = current.OwnerSubtreeForOne ?? current.OwnerSubtreeForZero;
            }

            var ownerForOne = _leaf.OwnerSubtreeForOne;
            var ownerForZero = _leaf.OwnerSubtreeForZero;
            var ownerBdd = _leaf.OwnerBDD;

            var subTree = new Subtree()
            {
                Port = item.Port,
            };
            
            if (item.Value)
            {
                subTree.TreeForOne = _leaf;
            }
            else
            {
                subTree.TreeForZero = _leaf;
            }

            subTree.OwnerSubtreeForOne = ownerForOne;
            subTree.OwnerSubtreeForZero = ownerForZero;
            subTree.OwnerBDD = ownerBdd;
        }

        private void ChangeAssignment(ISubtree current, TreeAssignment item)
        {

        }

        public override void Clear()
        {
            if (_leaf.OwnerBDD != null)
            {
                _leaf.OwnerBDD.Tree = _leaf;
            }
            else
            {
                _leaf.Delete();
            }
        }

        public override bool Remove(TreeAssignment item)
        {
            throw new NotImplementedException();
        }
    }
}
