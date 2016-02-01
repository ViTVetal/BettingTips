class Api::EventsController < ApplicationController
  def by_category
    @categories = Category.find(params[:id]).events

    render json: @categories, :include => { :league => { :only => :image_url } }
  end

  def single
    render :json => Event.find(params[:id])
  end
end