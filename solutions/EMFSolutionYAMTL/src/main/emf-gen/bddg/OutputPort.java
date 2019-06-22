/**
 */
package bddg;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Output Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link bddg.OutputPort#getAssignments <em>Assignments</em>}</li>
 * </ul>
 *
 * @see bddg.BddgPackage#getOutputPort()
 * @model
 * @generated
 */
public interface OutputPort extends Port {
	/**
	 * Returns the value of the '<em><b>Assignments</b></em>' reference list.
	 * The list contents are of type {@link bddg.Assignment}.
	 * It is bidirectional and its opposite is '{@link bddg.Assignment#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assignments</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assignments</em>' reference list.
	 * @see bddg.BddgPackage#getOutputPort_Assignments()
	 * @see bddg.Assignment#getPort
	 * @model opposite="port" ordered="false"
	 * @generated
	 */
	EList<Assignment> getAssignments();

} // OutputPort
