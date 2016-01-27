class Api::GcmsController < ApplicationController
  def create
  	Gcm.create(gcm_params)
  end

  def gcm_params
    params.permit(:token)
  end	
end