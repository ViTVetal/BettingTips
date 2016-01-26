class Event < ActiveRecord::Base
	belongs_to :category

	validates :team1, :team2, :tip, :date, :category, presence: true
	validates :success, presence: false
end
