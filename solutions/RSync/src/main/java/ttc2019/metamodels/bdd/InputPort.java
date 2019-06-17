/**
 */
package ttc2019.metamodels.bdd;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Input Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link ttc2019.metamodels.bdd.InputPort#getSubtrees <em>Subtrees</em>}</li>
 * </ul>
 *
 * @see ttc2019.metamodels.bdd.BDDPackage#getInputPort()
 * @model
 * @generated
 */
public interface InputPort extends Port {
	/**
	 * Returns the value of the '<em><b>Subtrees</b></em>' reference list.
	 * The list contents are of type {@link ttc2019.metamodels.bdd.Subtree}.
	 * It is bidirectional and its opposite is '{@link ttc2019.metamodels.bdd.Subtree#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subtrees</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subtrees</em>' reference list.
	 * @see ttc2019.metamodels.bdd.BDDPackage#getInputPort_Subtrees()
	 * @see ttc2019.metamodels.bdd.Subtree#getPort
	 * @model opposite="port" ordered="false"
	 * @generated
	 */
	EList<Subtree> getSubtrees();

} // InputPort
