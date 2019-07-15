/**
 */
package tt;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Row</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tt.Row#getOwner <em>Owner</em>}</li>
 *   <li>{@link tt.Row#getCells <em>Cells</em>}</li>
 * </ul>
 *
 * @see tt.TTPackage#getRow()
 * @model
 * @generated
 */
public interface Row extends LocatedElement {
	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link tt.TruthTable#getRows <em>Rows</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(TruthTable)
	 * @see tt.TTPackage#getRow_Owner()
	 * @see tt.TruthTable#getRows
	 * @model opposite="rows" required="true" transient="false" ordered="false"
	 * @generated
	 */
	TruthTable getOwner();

	/**
	 * Sets the value of the '{@link tt.Row#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(TruthTable value);

	/**
	 * Returns the value of the '<em><b>Cells</b></em>' containment reference list.
	 * The list contents are of type {@link tt.Cell}.
	 * It is bidirectional and its opposite is '{@link tt.Cell#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cells</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cells</em>' containment reference list.
	 * @see tt.TTPackage#getRow_Cells()
	 * @see tt.Cell#getOwner
	 * @model opposite="owner" containment="true" required="true" ordered="false"
	 * @generated
	 */
	EList<Cell> getCells();

} // Row
