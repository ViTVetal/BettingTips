class Api::GcmsController < ApplicationController
	skip_before_filter  :verify_authenticity_token
  def create
  	Gcm.create(gcm_params)
  end

  def gcm_params
    params.permit(:token)
  end	
end