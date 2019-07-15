/**
 */
package ttc2019.metamodels.bdd;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Leaf</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link ttc2019.metamodels.bdd.Leaf#getAssignments <em>Assignments</em>}</li>
 * </ul>
 *
 * @see ttc2019.metamodels.bdd.BDDPackage#getLeaf()
 * @model
 * @generated
 */
public interface Leaf extends Tree {
	/**
	 * Returns the value of the '<em><b>Assignments</b></em>' containment reference list.
	 * The list contents are of type {@link ttc2019.metamodels.bdd.Assignment}.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.Assignment#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assignments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assignments</em>' containment reference list.
	 * @see ttc2019.metamodels.bdd.BDDPackage#getLeaf_Assignments()
	 * @see ttc2019.metamodels.bdd.Assignment#getOwner
	 * @model opposite="owner" containment="true" required="true" ordered="false"
	 * @generated
	 */
	EList<Assignment> getAssignments();

} // Leaf
