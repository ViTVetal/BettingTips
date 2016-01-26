class Event < ActiveRecord::Base
	belongs_to :category

	validates :team1, :team2, :tip, :date, :category, :odds, presence: true
	validates :odds, numericality: true
	validates :score1, :score2, numericality: { only_integer: true, allow_blank: true }
end
