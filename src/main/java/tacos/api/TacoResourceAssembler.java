package tacos.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import tacos.Taco;

public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco, TacoResource>{

	public TacoResourceAssembler() {
		super(DesignTacoController.class, TacoResource.class);
	}
	
	@Override
	public TacoResource toModel(Taco taco) {
		// TODO Auto-generated method stub
		return createModelWithId(taco.getId(), taco);
	}
	
	@Override
	protected TacoResource instantiateModel(Taco taco) {
		// TODO Auto-generated method stub
		return new TacoResource(taco);
	}

}
