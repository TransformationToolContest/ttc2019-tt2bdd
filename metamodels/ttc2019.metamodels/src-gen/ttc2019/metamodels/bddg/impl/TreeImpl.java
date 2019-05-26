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

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import ttc2019.metamodels.bddg.BDD;
import ttc2019.metamodels.bddg.BDDGPackage;
import ttc2019.metamodels.bddg.Subtree;
import ttc2019.metamodels.bddg.Tree;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tree</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link ttc2019.metamodels.bddg.impl.TreeImpl#getOwnerBDD <em>Owner BDD</em>}</li>
 *   <li>{@link ttc2019.metamodels.bddg.impl.TreeImpl#getOwnerSubtreeForZero <em>Owner Subtree For Zero</em>}</li>
 *   <li>{@link ttc2019.metamodels.bddg.impl.TreeImpl#getOwnerSubtreeForOne <em>Owner Subtree For One</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TreeImpl extends MinimalEObjectImpl.Container implements Tree {
	/**
	 * The cached value of the '{@link #getOwnerSubtreeForZero() <em>Owner Subtree For Zero</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerSubtreeForZero()
	 * @generated
	 * @ordered
	 */
	protected EList<Subtree> ownerSubtreeForZero;

	/**
	 * The cached value of the '{@link #getOwnerSubtreeForOne() <em>Owner Subtree For One</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerSubtreeForOne()
	 * @generated
	 * @ordered
	 */
	protected EList<Subtree> ownerSubtreeForOne;

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
		return BDDGPackage.Literals.TREE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BDD getOwnerBDD() {
		if (eContainerFeatureID() != BDDGPackage.TREE__OWNER_BDD) return null;
		return (BDD)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwnerBDD(BDD newOwnerBDD, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwnerBDD, BDDGPackage.TREE__OWNER_BDD, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerBDD(BDD newOwnerBDD) {
		if (newOwnerBDD != eInternalContainer() || (eContainerFeatureID() != BDDGPackage.TREE__OWNER_BDD && newOwnerBDD != null)) {
			if (EcoreUtil.isAncestor(this, newOwnerBDD))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwnerBDD != null)
				msgs = ((InternalEObject)newOwnerBDD).eInverseAdd(this, BDDGPackage.BDD__TREES, BDD.class, msgs);
			msgs = basicSetOwnerBDD(newOwnerBDD, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BDDGPackage.TREE__OWNER_BDD, newOwnerBDD, newOwnerBDD));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Subtree> getOwnerSubtreeForZero() {
		if (ownerSubtreeForZero == null) {
			ownerSubtreeForZero = new EObjectWithInverseResolvingEList<Subtree>(Subtree.class, this, BDDGPackage.TREE__OWNER_SUBTREE_FOR_ZERO, BDDGPackage.SUBTREE__TREE_FOR_ZERO);
		}
		return ownerSubtreeForZero;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Subtree> getOwnerSubtreeForOne() {
		if (ownerSubtreeForOne == null) {
			ownerSubtreeForOne = new EObjectWithInverseResolvingEList<Subtree>(Subtree.class, this, BDDGPackage.TREE__OWNER_SUBTREE_FOR_ONE, BDDGPackage.SUBTREE__TREE_FOR_ONE);
		}
		return ownerSubtreeForOne;
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
			case BDDGPackage.TREE__OWNER_BDD:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwnerBDD((BDD)otherEnd, msgs);
			case BDDGPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOwnerSubtreeForZero()).basicAdd(otherEnd, msgs);
			case BDDGPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOwnerSubtreeForOne()).basicAdd(otherEnd, msgs);
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
			case BDDGPackage.TREE__OWNER_BDD:
				return basicSetOwnerBDD(null, msgs);
			case BDDGPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				return ((InternalEList<?>)getOwnerSubtreeForZero()).basicRemove(otherEnd, msgs);
			case BDDGPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				return ((InternalEList<?>)getOwnerSubtreeForOne()).basicRemove(otherEnd, msgs);
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
			case BDDGPackage.TREE__OWNER_BDD:
				return eInternalContainer().eInverseRemove(this, BDDGPackage.BDD__TREES, BDD.class, msgs);
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
			case BDDGPackage.TREE__OWNER_BDD:
				return getOwnerBDD();
			case BDDGPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				return getOwnerSubtreeForZero();
			case BDDGPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				return getOwnerSubtreeForOne();
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
			case BDDGPackage.TREE__OWNER_BDD:
				setOwnerBDD((BDD)newValue);
				return;
			case BDDGPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				getOwnerSubtreeForZero().clear();
				getOwnerSubtreeForZero().addAll((Collection<? extends Subtree>)newValue);
				return;
			case BDDGPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				getOwnerSubtreeForOne().clear();
				getOwnerSubtreeForOne().addAll((Collection<? extends Subtree>)newValue);
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
			case BDDGPackage.TREE__OWNER_BDD:
				setOwnerBDD((BDD)null);
				return;
			case BDDGPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				getOwnerSubtreeForZero().clear();
				return;
			case BDDGPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				getOwnerSubtreeForOne().clear();
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
			case BDDGPackage.TREE__OWNER_BDD:
				return getOwnerBDD() != null;
			case BDDGPackage.TREE__OWNER_SUBTREE_FOR_ZERO:
				return ownerSubtreeForZero != null && !ownerSubtreeForZero.isEmpty();
			case BDDGPackage.TREE__OWNER_SUBTREE_FOR_ONE:
				return ownerSubtreeForOne != null && !ownerSubtreeForOne.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TreeImpl
