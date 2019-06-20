package ttc2019.metamodels.create;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import java.util.HashMap;

class IntrinsicIDXMIResourceFactoryImpl extends XMIResourceFactoryImpl {
  @Override
  public Resource createResource(URI uri) {
    final XMIResourceImpl r = new XMIResourceImpl(uri);
    r.setIntrinsicIDToEObjectMap(new HashMap<>());
    return r;
  }
}

