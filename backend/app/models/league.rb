class League < ActiveRecord::Base
	has_many :events

	validates :name, :image_url, presence: true
end
