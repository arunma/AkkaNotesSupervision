package me.rerun.akkanotes.protocols

object QuoteRepositoryProtocol{

  case class QuoteRepositoryRequest()
  case class QuoteRepositoryResponse(quoteString:String)
  
}