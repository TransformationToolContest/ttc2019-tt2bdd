/**
 */
package ttc2019.metamodels.bdd.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import ttc2019.metamodels.bdd.BDDPackage;
import ttc2019.metamodels.bdd.InputPort;
import ttc2019.metamodels.bdd.Subtree;
import ttc2019.metamodels.bdd.Tree;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subtree</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link ttc2019.metamodels.bdd.impl.SubtreeImpl#getPort <em>Port</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.impl.SubtreeImpl#getTreeForZero <em>Tree For Zero</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.impl.SubtreeImpl#getTreeForOne <em>Tree For One</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SubtreeImpl extends TreeImpl implements Subtree {
	/**
	 * The cached value of the '{@link #getPort() <em>Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPort()
	 * @generated
	 * @ordered
	 */
	protected InputPort port;

	/**
	 * The cached value of the '{@link #getTreeForZero() <em>Tree For Zero</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTreeForZero()
	 * @generated
	 * @ordered
	 */
	protected Tree treeForZero;

	/**
	 * The cached value of the '{@link #getTreeForOne() <em>Tree For One</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTreeForOne()
	 * @generated
	 * @ordered
	 */
	protected Tree treeForOne;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubtreeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BDDPackage.Literals.SUBTREE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputPort getPort() {
		if (port != null && port.eIsProxy()) {
			InternalEObject oldPort = (InternalEObject)port;
			port = (InputPort)eResolveProxy(oldPort);
			if (port != oldPort) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BDDPackage.SUBTREE__PORT, oldPort, port));
			}
		}
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputPort basicGetPort() {
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPort(InputPort newPort, NotificationChain msgs) {
		InputPort oldPort = port;
		port = newPort;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BDDPackage.SUBTREE__PORT, oldPort, newPort);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPort(InputPort newPort) {
		if (newPort != port) {
			NotificationChain msgs = null;
			if (port != null)
				msgs = ((InternalEObject)port).eInverseRemove(this, BDDPackage.INPUT_PORT__SUBTREES, InputPort.class, msgs);
			if (newPort != null)
				msgs = ((InternalEObject)newPort).eInverseAdd(this, BDDPackage.INPUT_PORT__SUBTREES, InputPort.class, msgs);
			msgs = basicSetPort(newPort, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BDDPackage.SUBTREE__PORT, newPort, newPort));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tree getTreeForZero() {
		return treeForZero;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTreeForZero(Tree newTreeForZero, NotificationChain msgs) {
		Tree oldTreeForZero = treeForZero;
		treeForZero = newTreeForZero;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BDDPackage.SUBTREE__TREE_FOR_ZERO, oldTreeForZero, newTreeForZero);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTreeForZero(Tree newTreeForZero) {
		if (newTreeForZero != treeForZero) {
			NotificationChain msgs = null;
			if (treeForZero != null)
				msgs = ((InternalEObject)treeForZero).eInverseRemove(this, BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO, Tree.class, msgs);
			if (newTreeForZero != null)
				msgs = ((InternalEObject)newTreeForZero).eInverseAdd(this, BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO, Tree.class, msgs);
			msgs = basicSetTreeForZero(newTreeForZero, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BDDPackage.SUBTREE__TREE_FOR_ZERO, newTreeForZero, newTreeForZero));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tree getTreeForOne() {
		return treeForOne;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTreeForOne(Tree newTreeForOne, NotificationChain msgs) {
		Tree oldTreeForOne = treeForOne;
		treeForOne = newTreeForOne;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BDDPackage.SUBTREE__TREE_FOR_ONE, oldTreeForOne, newTreeForOne);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTreeForOne(Tree newTreeForOne) {
		if (newTreeForOne != treeForOne) {
			NotificationChain msgs = null;
			if (treeForOne != null)
				msgs = ((InternalEObject)treeForOne).eInverseRemove(this, BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE, Tree.class, msgs);
			if (newTreeForOne != null)
				msgs = ((InternalEObject)newTreeForOne).eInverseAdd(this, BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE, Tree.class, msgs);
			msgs = basicSetTreeForOne(newTreeForOne, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BDDPackage.SUBTREE__TREE_FOR_ONE, newTreeForOne, newTreeForOne));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BDDPackage.SUBTREE__PORT:
				if (port != null)
					msgs = ((InternalEObject)port).eInverseRemove(this, BDDPackage.INPUT_PORT__SUBTREES, InputPort.class, msgs);
				return basicSetPort((InputPort)otherEnd, msgs);
			case BDDPackage.SUBTREE__TREE_FOR_ZERO:
				if (treeForZero != null)
					msgs = ((InternalEObject)treeForZero).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BDDPackage.SUBTREE__TREE_FOR_ZERO, null, msgs);
				return basicSetTreeForZero((Tree)otherEnd, msgs);
			case BDDPackage.SUBTREE__TREE_FOR_ONE:
				if (treeForOne != null)
					msgs = ((InternalEObject)treeForOne).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BDDPackage.SUBTREE__TREE_FOR_ONE, null, msgs);
				return basicSetTreeForOne((Tree)otherEnd, msgs);
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
			case BDDPackage.SUBTREE__PORT:
				return basicSetPort(null, msgs);
			case BDDPackage.SUBTREE__TREE_FOR_ZERO:
				return basicSetTreeForZero(null, msgs);
			case BDDPackage.SUBTREE__TREE_FOR_ONE:
				return basicSetTreeForOne(null, msgs);
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
			case BDDPackage.SUBTREE__PORT:
				if (resolve) return getPort();
				return basicGetPort();
			case BDDPackage.SUBTREE__TREE_FOR_ZERO:
				return getTreeForZero();
			case BDDPackage.SUBTREE__TREE_FOR_ONE:
				return getTreeForOne();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BDDPackage.SUBTREE__PORT:
				setPort((InputPort)newValue);
				return;
			case BDDPackage.SUBTREE__TREE_FOR_ZERO:
				setTreeForZero((Tree)newValue);
				return;
			case BDDPackage.SUBTREE__TREE_FOR_ONE:
				setTreeForOne((Tree)newValue);
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
			case BDDPackage.SUBTREE__PORT:
				setPort((InputPort)null);
				return;
			case BDDPackage.SUBTREE__TREE_FOR_ZERO:
				setTreeForZero((Tree)null);
				return;
			case BDDPackage.SUBTREE__TREE_FOR_ONE:
				setTreeForOne((Tree)null);
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
			case BDDPackage.SUBTREE__PORT:
				return port != null;
			case BDDPackage.SUBTREE__TREE_FOR_ZERO:
				return treeForZero != null;
			case BDDPackage.SUBTREE__TREE_FOR_ONE:
				return treeForOne != null;
		}
		return super.eIsSet(featureID);
	}

} //SubtreeImpl
