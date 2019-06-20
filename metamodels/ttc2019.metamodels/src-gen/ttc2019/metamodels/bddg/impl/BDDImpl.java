/**
 */
package ttc2019.metamodels.bddg.impl;

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

import ttc2019.metamodels.bddg.BDD;
import ttc2019.metamodels.bddg.BDDGPackage;
import ttc2019.metamodels.bddg.Port;
import ttc2019.metamodels.bddg.Tree;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>BDD</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link ttc2019.metamodels.bddg.impl.BDDImpl#getName <em>Name</em>}</li>
 *   <li>{@link ttc2019.metamodels.bddg.impl.BDDImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link ttc2019.metamodels.bddg.impl.BDDImpl#getRoot <em>Root</em>}</li>
 *   <li>{@link ttc2019.metamodels.bddg.impl.BDDImpl#getTrees <em>Trees</em>}</li>
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
	 * The cached value of the '{@link #getRoot() <em>Root</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoot()
	 * @generated
	 * @ordered
	 */
	protected Tree root;

	/**
	 * The cached value of the '{@link #getTrees() <em>Trees</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrees()
	 * @generated
	 * @ordered
	 */
	protected EList<Tree> trees;

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
		return BDDGPackage.Literals.BDD;
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
			eNotify(new ENotificationImpl(this, Notification.SET, BDDGPackage.BDD__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentWithInverseEList<Port>(Port.class, this, BDDGPackage.BDD__PORTS, BDDGPackage.PORT__OWNER);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tree getRoot() {
		if (root != null && root.eIsProxy()) {
			InternalEObject oldRoot = (InternalEObject)root;
			root = (Tree)eResolveProxy(oldRoot);
			if (root != oldRoot) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BDDGPackage.BDD__ROOT, oldRoot, root));
			}
		}
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tree basicGetRoot() {
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRoot(Tree newRoot) {
		Tree oldRoot = root;
		root = newRoot;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BDDGPackage.BDD__ROOT, oldRoot, root));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Tree> getTrees() {
		if (trees == null) {
			trees = new EObjectContainmentWithInverseEList<Tree>(Tree.class, this, BDDGPackage.BDD__TREES, BDDGPackage.TREE__OWNER_BDD);
		}
		return trees;
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
			case BDDGPackage.BDD__PORTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPorts()).basicAdd(otherEnd, msgs);
			case BDDGPackage.BDD__TREES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getTrees()).basicAdd(otherEnd, msgs);
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
			case BDDGPackage.BDD__PORTS:
				return ((InternalEList<?>)getPorts()).basicRemove(otherEnd, msgs);
			case BDDGPackage.BDD__TREES:
				return ((InternalEList<?>)getTrees()).basicRemove(otherEnd, msgs);
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
			case BDDGPackage.BDD__NAME:
				return getName();
			case BDDGPackage.BDD__PORTS:
				return getPorts();
			case BDDGPackage.BDD__ROOT:
				if (resolve) return getRoot();
				return basicGetRoot();
			case BDDGPackage.BDD__TREES:
				return getTrees();
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
			case BDDGPackage.BDD__NAME:
				setName((String)newValue);
				return;
			case BDDGPackage.BDD__PORTS:
				getPorts().clear();
				getPorts().addAll((Collection<? extends Port>)newValue);
				return;
			case BDDGPackage.BDD__ROOT:
				setRoot((Tree)newValue);
				return;
			case BDDGPackage.BDD__TREES:
				getTrees().clear();
				getTrees().addAll((Collection<? extends Tree>)newValue);
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
			case BDDGPackage.BDD__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BDDGPackage.BDD__PORTS:
				getPorts().clear();
				return;
			case BDDGPackage.BDD__ROOT:
				setRoot((Tree)null);
				return;
			case BDDGPackage.BDD__TREES:
				getTrees().clear();
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
			case BDDGPackage.BDD__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BDDGPackage.BDD__PORTS:
				return ports != null && !ports.isEmpty();
			case BDDGPackage.BDD__ROOT:
				return root != null;
			case BDDGPackage.BDD__TREES:
				return trees != null && !trees.isEmpty();
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
