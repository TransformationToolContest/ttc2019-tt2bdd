/**
 */
package ttc2019.metamodels.bdd.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import ttc2019.metamodels.bdd.BDD;
import ttc2019.metamodels.bdd.BDDPackage;
import ttc2019.metamodels.bdd.Subtree;
import ttc2019.metamodels.bdd.Tree;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tree</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link ttc2019.metamodels.bdd.impl.TreeImpl#getOwnerBDD <em>Owner BDD</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.impl.TreeImpl#getOwnerSubtreeForZero <em>Owner Subtree For Zero</em>}</li>
 *   <li>{@link ttc2019.metamodels.bdd.impl.TreeImpl#getOwnerSubtreeForOne <em>Owner Subtree For One</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TreeImpl extends MinimalEObjectImpl.Container implements Tree {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TreeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BDDPackage.Literals.TREE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BDD getOwnerBDD() {
		if (eContainerFeatureID() != BDDPackage.TREE__OWNER_BDD) return null;
		return (BDD)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwnerBDD(BDD newOwnerBDD, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwnerBDD, BDDPackage.TREE__OWNER_BDD, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerBDD(BDD newOwnerBDD) {
		if (newOwnerBDD != eInternalContainer() || (eContainerFeatureID() != BDDPackage.TREE__OWNER_BDD && newOwnerBDD != null)) {
			if (EcoreUtil.isAncestor(this, newOwnerBDD))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwnerBDD != null)
				msgs = ((InternalEObject)newOwnerBDD).eInverseAdd(this, BDDPackage.BDD__TREE, BDD.class, msgs);
			msgs = basicSetOwnerBDD(newOwnerBDD, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BDDPackage.TREE__OWNER_BDD, newOwnerBDD, newOwnerBDD));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Subtree getOwnerSubtreeForZero() {
		if (eContainerFeatureID() != BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO) return null;
		return (Subtree)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwnerSubtreeForZero(Subtree newOwnerSubtreeForZero, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwnerSubtreeForZero, BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerSubtreeForZero(Subtree newOwnerSubtreeForZero) {
		if (newOwnerSubtreeForZero != eInternalContainer() || (eContainerFeatureID() != BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO && newOwnerSubtreeForZero != null)) {
			if (EcoreUtil.isAncestor(this, newOwnerSubtreeForZero))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwnerSubtreeForZero != null)
				msgs = ((InternalEObject)newOwnerSubtreeForZero).eInverseAdd(this, BDDPackage.SUBTREE__TREE_FOR_ZERO, Subtree.class, msgs);
			msgs = basicSetOwnerSubtreeForZero(newOwnerSubtreeForZero, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO, newOwnerSubtreeForZero, newOwnerSubtreeForZero));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Subtree getOwnerSubtreeForOne() {
		if (eContainerFeatureID() != BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE) return null;
		return (Subtree)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwnerSubtreeForOne(Subtree newOwnerSubtreeForOne, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwnerSubtreeForOne, BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerSubtreeForOne(Subtree newOwnerSubtreeForOne) {
		if (newOwnerSubtreeForOne != eInternalContainer() || (eContainerFeatureID() != BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE && newOwnerSubtreeForOne != null)) {
			if (EcoreUtil.isAncestor(this, newOwnerSubtreeForOne))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwnerSubtreeForOne != null)
				msgs = ((InternalEObject)newOwnerSubtreeForOne).eInverseAdd(this, BDDPackage.SUBTREE__TREE_FOR_ONE, Subtree.class, msgs);
			msgs = basicSetOwnerSubtreeForOne(newOwnerSubtreeForOne, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE, newOwnerSubtreeForOne, newOwnerSubtreeForOne));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BDDPackage.TREE__OWNER_BDD:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwnerBDD((BDD)otherEnd, msgs);
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwnerSubtreeForZero((Subtree)otherEnd, msgs);
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwnerSubtreeForOne((Subtree)otherEnd, msgs);
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
			case BDDPackage.TREE__OWNER_BDD:
				return basicSetOwnerBDD(null, msgs);
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				return basicSetOwnerSubtreeForZero(null, msgs);
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				return basicSetOwnerSubtreeForOne(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case BDDPackage.TREE__OWNER_BDD:
				return eInternalContainer().eInverseRemove(this, BDDPackage.BDD__TREE, BDD.class, msgs);
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				return eInternalContainer().eInverseRemove(this, BDDPackage.SUBTREE__TREE_FOR_ZERO, Subtree.class, msgs);
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				return eInternalContainer().eInverseRemove(this, BDDPackage.SUBTREE__TREE_FOR_ONE, Subtree.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BDDPackage.TREE__OWNER_BDD:
				return getOwnerBDD();
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				return getOwnerSubtreeForZero();
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				return getOwnerSubtreeForOne();
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
			case BDDPackage.TREE__OWNER_BDD:
				setOwnerBDD((BDD)newValue);
				return;
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				setOwnerSubtreeForZero((Subtree)newValue);
				return;
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				setOwnerSubtreeForOne((Subtree)newValue);
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
			case BDDPackage.TREE__OWNER_BDD:
				setOwnerBDD((BDD)null);
				return;
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				setOwnerSubtreeForZero((Subtree)null);
				return;
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				setOwnerSubtreeForOne((Subtree)null);
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
			case BDDPackage.TREE__OWNER_BDD:
				return getOwnerBDD() != null;
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				return getOwnerSubtreeForZero() != null;
			case BDDPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				return getOwnerSubtreeForOne() != null;
		}
		return super.eIsSet(featureID);
	}

} //TreeImpl
