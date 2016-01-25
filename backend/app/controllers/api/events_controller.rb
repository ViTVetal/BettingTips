class Api::EventsController < ApplicationController
  def by_category
    render :json => Category.find(params[:id]).events
  end

  def single
    render :json => Event.find(params[:id])
  end
end