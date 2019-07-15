/**
 */
package ttc2019.metamodels.bdd.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import ttc2019.metamodels.bdd.BDD;
import ttc2019.metamodels.bdd.BDDPackage;
import ttc2019.metamodels.bdd.Port;
import ttc2019.metamodels.bdd.Tree;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>BDD</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link ttc2019.metamodels.bdd.impl.BDDImpl#getName <em>Name</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.impl.BDDImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.impl.BDDImpl#getTree <em>Tree</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BDDImpl extends MinimalEObjectImpl.Container implements BDD {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> ports;

	/**
	 * The cached value of the '{@link #getTree() <em>Tree</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTree()
	 * @generated
	 * @ordered
	 */
	protected Tree tree;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BDDImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BDDPackage.Literals.BDD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BDDPackage.BDD__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentWithInverseEList<Port>(Port.class, this, BDDPackage.BDD__PORTS, BDDPackage.PORT__OWNER);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tree getTree() {
		return tree;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTree(Tree newTree, NotificationChain msgs) {
		Tree oldTree = tree;
		tree = newTree;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BDDPackage.BDD__TREE, oldTree, newTree);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTree(Tree newTree) {
		if (newTree != tree) {
			NotificationChain msgs = null;
			if (tree != null)
				msgs = ((InternalEObject)tree).eInverseRemove(this, BDDPackage.TREE__OWNER_BDD, Tree.class, msgs);
			if (newTree != null)
				msgs = ((InternalEObject)newTree).eInverseAdd(this, BDDPackage.TREE__OWNER_BDD, Tree.class, msgs);
			msgs = basicSetTree(newTree, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BDDPackage.BDD__TREE, newTree, newTree));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BDDPackage.BDD__PORTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPorts()).basicAdd(otherEnd, msgs);
			case BDDPackage.BDD__TREE:
				if (tree != null)
					msgs = ((InternalEObject)tree).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BDDPackage.BDD__TREE, null, msgs);
				return basicSetTree((Tree)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BDDPackage.BDD__PORTS:
				return ((InternalEList<?>)getPorts()).basicRemove(otherEnd, msgs);
			case BDDPackage.BDD__TREE:
				return basicSetTree(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BDDPackage.BDD__NAME:
				return getName();
			case BDDPackage.BDD__PORTS:
				return getPorts();
			case BDDPackage.BDD__TREE:
				return getTree();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BDDPackage.BDD__NAME:
				setName((String)newValue);
				return;
			case BDDPackage.BDD__PORTS:
				getPorts().clear();
				getPorts().addAll((Collection<? extends Port>)newValue);
				return;
			case BDDPackage.BDD__TREE:
				setTree((Tree)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BDDPackage.BDD__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BDDPackage.BDD__PORTS:
				getPorts().clear();
				return;
			case BDDPackage.BDD__TREE:
				setTree((Tree)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BDDPackage.BDD__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BDDPackage.BDD__PORTS:
				return ports != null && !ports.isEmpty();
			case BDDPackage.BDD__TREE:
				return tree != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //BDDImpl
