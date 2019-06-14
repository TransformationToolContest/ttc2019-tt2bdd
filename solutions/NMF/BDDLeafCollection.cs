using NMF.Collections.ObjectModel;
using NMF.Expressions.Linq;
using NMF.Models;
using System;
using System.Collections.Generic;
using System.Text;
using TTC2019.BinaryDecision.Metamodels.BinaryDecisionDiagrams.BDD;

namespace TTC2019.BinaryDecision
{

    internal class BDDLeafCollection : CustomCollection<ILeaf>
    {
        private readonly BDD _bdd;

        public BDDLeafCollection(BDD bdd) : base(bdd.Descendants().OfType<ILeaf>())
        {
            _bdd = bdd;
        }

        public override void Add(ILeaf item)
        {
            ITree current = item;
            var assignments = new Dictionary<IInputPort, bool>();
            var treeStack = new Stack<ISubtree>();
            while (current != null)
            {
                if (current.OwnerSubtreeForOne != null)
                {
                    assignments.Add(current.OwnerSubtreeForOne.Port, true);
                    treeStack.Push(current.OwnerSubtreeForOne);
                    current = current.OwnerSubtreeForOne;
                }
                else if (current.OwnerSubtreeForZero != null)
                {
                    assignments.Add(current.OwnerSubtreeForZero.Port, false);
                    treeStack.Push(current.OwnerSubtreeForZero);
                    current = current.OwnerSubtreeForZero;
                }
                else
                {
                    if (_bdd.Tree == null)
                    {
                        _bdd.Tree = current;
                        return;
                    }
                    break;
                }
            }

            current = _bdd.Tree;
            while (current != null)
            {
                if (current is ISubtree subTree)
                {
                    if (assignments.TryGetValue(subTree.Port, out var assignedValue))
                    {
                        if (assignedValue)
                        {
                            current = subTree.TreeForOne;
                        }
                        else
                        {
                            current = subTree.TreeForZero;
                        }
                        if (treeStack != null && treeStack.Pop().Port != subTree.Port)
                        {
                            treeStack = null;
                        }
                        assignments.Remove(subTree.Port);
                        if (current == null)
                        {
                            if (treeStack != null)
                            {
                                ITree value = treeStack.Count > 0 ? treeStack.Pop() as ITree : item;
                                if (assignedValue)
                                {
                                    subTree.TreeForOne = value;
                                }
                                else
                                {
                                    subTree.TreeForZero = value;
                                }
                            }
                            else
                            {
                                ITree value = item;
                                foreach (var assignment in assignments)
                                {
                                    var tempTree = new Subtree() { Port = assignment.Key };
                                    if (assignment.Value)
                                    {
                                        tempTree.TreeForOne = value;
                                    }
                                    else
                                    {
                                        tempTree.TreeForZero = value;
                                    }
                                    value = tempTree;
                                }
                                if (assignedValue)
                                {
                                    subTree.TreeForOne = value;
                                }
                                else
                                {
                                    subTree.TreeForZero = value;
                                }
                            }
                            return;
                        }
                    }
                    else
                    {
                        // the new child will dominate all existing ones
                        if (treeStack != null && treeStack.Count > 0)
                        {
                            var ownerForZero = subTree.OwnerSubtreeForZero;
                            var ownerForOne = subTree.OwnerSubtreeForOne;
                            var ownerBdd = subTree.OwnerBDD;
                            var peek = treeStack.Peek();
                            if (peek.TreeForZero == null)
                            {
                                peek.TreeForZero = subTree;
                            }
                            else if (peek.TreeForOne == null)
                            {
                                peek.TreeForOne = subTree;
                            }
                            if (peek == ownerForOne || peek == ownerForZero)
                            {

                            }
                            // we need to be careful not to accidently delete peek
                            if (ownerForOne != null)
                            {
                                peek.OwnerSubtreeForOne = ownerForOne;
                            }
                            else if (ownerForZero != null)
                            {
                                peek.OwnerSubtreeForZero = ownerForZero;
                            }
                            else if (ownerBdd != null)
                            {
                                peek.OwnerBDD = ownerBdd;
                            }
                            return;
                        }
                        else
                        {
                            throw new NotSupportedException();
                        }
                    }
                }
            }
        }

        public override void Clear()
        {
            _bdd.Tree = null;
        }

        public override bool Remove(ILeaf item)
        {
            item.Delete();
            return true;
        }
    }
}
