interface ActionResult<D = void> {
  success: boolean;
  data?: D;
  errors?: string[];
}

export default ActionResult;
