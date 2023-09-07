package org.custom.appcontext.testobj.web.allcontrollers;

import java.math.BigDecimal;
import org.custom.appcontext.testobj.web.Price;
import org.custom.web.annotations.Delete;
import org.custom.web.annotations.Get;
import org.custom.web.annotations.PathVariable;
import org.custom.web.annotations.Post;
import org.custom.web.annotations.Put;
import org.custom.web.annotations.RequestBody;
import org.custom.web.annotations.RestController;
import org.custom.web.model.HttpStatusCode;
import org.custom.web.model.RestResponse;

@RestController
public class PriceController {

  @Get(url = "/prices/{productId}")
  public RestResponse<Price> getPriceByProductId(@PathVariable long productId) {
    return new RestResponse<>(new Price(BigDecimal.valueOf(29.99), productId), HttpStatusCode.OK);
  }

  @Post(url = "/prices")
  public RestResponse<String> createPrice(@RequestBody Price newPrice) {
    return new RestResponse<>(newPrice.toString(), HttpStatusCode.OK);
  }

  @Put(url = "/prices/{productId}")
  public RestResponse<String> updatePrice(
      @PathVariable long productId, @RequestBody Price updatedPrice) {
    return new RestResponse<>(updatedPrice.toString(), HttpStatusCode.OK);
  }

  @Delete(url = "/prices/{productId}")
  public String deletePrice(@PathVariable() long productId) {
    return "Deleted: " + productId;
  }
}
